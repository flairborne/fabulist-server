package com.flairborne.fabulist.runtime.element.part.linkage;

import com.flairborne.fabulist.runtime.element.ElementId;
import com.flairborne.fabulist.runtime.context.Blocking;

/**
 * A {@link Blocking non-blocking} linkage that simply lets the node continue to the next node.
 */
public class Passthrough extends AbstractLinkage {

    public static class Builder extends AbstractLinkage.Builder<Builder> {

        public Builder(ElementId previous, ElementId next) {
            super(previous, next);
        }

        public Builder(String previous, String next) {
            super(previous, next);
        }

        @Override
        public Linkage build() {
            return new Passthrough(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private Passthrough(Builder builder) {
        super(builder);
    }

    @Override
    public boolean isBlocking() {
        return false;
    }
}
