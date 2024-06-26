package com.flairborne.fabulist.runtime.element.part.node;

import com.flairborne.fabulist.runtime.element.AbstractElement;
import com.flairborne.fabulist.runtime.element.ElementId;
import com.flairborne.fabulist.runtime.context.action.AbstractAction;
import com.flairborne.fabulist.runtime.context.action.Action;
import com.flairborne.fabulist.runtime.element.part.linkage.AbstractLinkage;
import com.flairborne.fabulist.runtime.element.part.linkage.Linkage;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This class provides a skeletal implementation of the {@link Node} interface.
 */
public abstract class AbstractNode extends AbstractElement implements Node {

    // TODO: Queue of actions might disable backtracking or undo functionality
    protected final Queue<Action> actions;
    protected final List<Linkage> linkages;
    protected final boolean isBlocking;

    // Builder is a generic type with a recursive type parameter
    public abstract static class Builder<T extends Builder<T>> {

        protected final ElementId id;
        protected final Queue<Action> actions;
        protected final List<Linkage> linkages;
        private boolean isBlocking;

        public Builder(String id) {
            this(ElementId.from(id));
        }

        public Builder(ElementId id) {
            this.id = id;
            actions = new LinkedList<>();
            linkages = new LinkedList<>();
        }

        public T withActions(AbstractAction.Builder<?>... builders) {
            for (AbstractAction.Builder<?> builder : builders) {
                addAction(builder);
            }

            return self();
        }

        public T addAction(AbstractAction.Builder<?> builder) {
            return addAction(builder.build());
        }

        public T addAction(Action action) {
            actions.add(action);
            return self();
        }

        public T withLinkages(AbstractLinkage.Builder<?>... builders) {
            for (AbstractLinkage.Builder<?> builder : builders) {
                addLinkage(builder);
            }

            return self();
        }

        public T addLinkage(AbstractLinkage.Builder<?> builder) {
            return addLinkage(builder.build());
        }

        public T addLinkage(Linkage linkage) {
            if (!linkage.previousId().equals(id)) {
                throw new IllegalArgumentException("Cannot add linkage that does not originate from this node");
            }

            if (!isBlocking && linkage.isBlocking()) {
                isBlocking = true;
            }

            linkages.add(linkage);
            return self();
        }

        public abstract Node build();

        // A simulated self-type idiom
        protected abstract T self();
    }

    protected AbstractNode(Builder<?> builder) {
        super(builder.id);
        actions = builder.actions;
        linkages = Collections.unmodifiableList(builder.linkages);
        isBlocking = builder.isBlocking;
    }

    @Override
    public Queue<Action> actions() {
        return actions;
    }

    @Override
    public List<Linkage> linkages() {
        return linkages;
    }

    @Override
    public boolean hasLinkages() {
        return !linkages.isEmpty();
    }

    /**
     * @return whether node is blocking which means at least one of its linkages is blocking
     */
    @Override
    public boolean isBlocking() {
        return isBlocking;
    }
}
