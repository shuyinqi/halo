
package org.mapbar.halo.data;
/**
 * 比较两个对象,
 * 
 * 
 */
public class Couple<T, U> {

    /** The first object. */
    private volatile T first;

    /** The second object. */
    private volatile U second;

    /**
     * Constructor.
     * 
     * @param first
     *            The first object.
     * @param second
     *            The second object.
     */
    public Couple(T first, U second) {
        this.first = first;
        this.second = second;
    }
    @Override
    public boolean equals(Object other) {
        boolean result = (this == other);

        if (!result && (other instanceof Couple)) {
            Couple<?, ?> couple = (Couple<?, ?>) other;

            if (((couple.getFirst() == null) && (getFirst() == null))
                    || ((getFirst() != null) && getFirst().equals(
                            couple.getFirst()))) {
                result = (((couple.getSecond() == null) && (getSecond() == null)) || ((getSecond() != null) && getSecond()
                        .equals(couple.getSecond())));
            }
        }

        return result;
    }

    /**
     * Returns the first object.
     * 
     * @return The first object.
     */
    public T getFirst() {
        return first;
    }

    /**
     * Returns the second object.
     * 
     * @return The second object.
     */
    public U getSecond() {
        return second;
    }

    /**
     * Sets the first object.
     * 
     * @param first
     *            The first object.
     */
    public void setFirst(T first) {
        this.first = first;
    }

    /**
     * Sets the second object.
     * 
     * @param second
     *            The second object.
     */
    public void setSecond(U second) {
        this.second = second;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return hashCode(getFirst(), getSecond());
    }
    /**
     * Computes the hash code of a set of objects. Follows the algorithm
     * specified in List.hasCode().
     * 
     * @param objects
     *            the objects to compute the hashCode
     * 
     * @return The hash code of a set of objects.
     */
    private  int hashCode(Object... objects) {
        int result = 1;

        if (objects != null) {
            for (final Object obj : objects) {
                result = 31 * result + (obj == null ? 0 : obj.hashCode());
            }
        }

        return result;
    }
    @Override
    public String toString() {
        return "(" + getFirst() + "," + getSecond() + ")";
    }
}
