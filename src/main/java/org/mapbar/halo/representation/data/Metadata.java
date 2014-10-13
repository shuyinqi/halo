package org.mapbar.halo.representation.data;
/***
 * 
 * @（#）:Metadata.java 
 * @description:  
 * @author:  sunjy@mapbar.com  2013-5-6 
 * @version: [SVN] 
 * @modify: 
 * @Copyright:  图吧
 */
public abstract class Metadata {
    /** The metadata name like "text/html" or "compress" or "iso-8851-1". */
    private final String name;

    /** The description of this metadata. */
    private final String description;

    /**
     * Returns the parent metadata if available or null.
     * 
     * @return The parent metadata.
     */
    public abstract Metadata getParent();

    /**
     * Indicates if a given metadata is included in the current one. The test is
     * true if both metadata are equal or if the given metadata is within the
     * range of the current one. For example, {@link MediaType#ALL} includes all
     * media types.
     * <p>
     * Examples:
     * <ul>
     * <li>TEXT_ALL.includes(TEXT_PLAIN) -> true</li>
     * <li>TEXT_PLAIN.includes(TEXT_ALL) -> false</li>
     * </ul>
     * 
     * @param included
     *            The metadata to test for inclusion.
     * @return True if the given metadata is included in the current one.
     * @see #isCompatible(Metadata)
     */
    public abstract boolean includes(Metadata included);

    /**
     * Checks if this metadata is compatible with the given metadata.
     * <p>
     * Examples:
     * <ul>
     * <li>TEXT_ALL.isCompatible(TEXT_PLAIN) -> true</li>
     * <li>TEXT_PLAIN.isCompatible(TEXT_ALL) -> true</li>
     * <li>TEXT_PLAIN.isCompatible(APPLICATION_ALL) -> false</li>
     * </ul>
     * 
     * @param otherMetadata
     *            The other metadata to compare.
     * @return True if the metadata are compatible.
     * @see #includes(Metadata)
     */
    public boolean isCompatible(Metadata otherMetadata) {
        return (otherMetadata != null)
                && (includes(otherMetadata) || otherMetadata.includes(this));
    }

    /**
     * Constructor.
     * 
     * @param name
     *            The unique name.
     */
    public Metadata(String name) {
        this(name, null);
    }

    /**
     * Constructor.
     * 
     * @param name
     *            The unique name.
     * @param description
     *            The description.
     */
    public Metadata(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object object) {
        return (object instanceof Metadata)
                && ((Metadata) object).getName().equals(getName());
    }

    /**
     * Returns the description.
     * 
     * @return The description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the name (ex: "text/html" or "compress" or "iso-8851-1").
     * 
     * @return The name (ex: "text/html" or "compress" or "iso-8851-1").
     */
    public String getName() {
        return this.name;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return (getName() == null) ? 0 : getName().hashCode();
    }

    /**
     * Returns the metadata name.
     * 
     * @return The metadata name.
     */
    @Override
    public String toString() {
        return getName();
    }
}