package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.person.messages.TagMessages.MESSAGE_CONSTRAINTS;
import static seedu.address.model.person.messages.TagMessages.VALIDATION_REGEX;

import java.util.Set;

import seedu.address.model.person.Attribute;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in
 * {@link #isValidTagName(String)}
 */
public class Tag extends Attribute<String> {

    public static final Set<Tag> EMPTY_TAG_SET = Set.of(new Tag(false));

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        super(tagName);
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
    }

    /**
     * Constructs an empty tag "[]"
     *
     * @param bool any boolean
     */
    private Tag(boolean bool) {
        super("");
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Determine if the tag value stored is a match with a specified string.
     * Returns true if specified value is an exact match of the tag value stored (case-insensitive).
     *
     * @param otherValue Other value to check against
     *
     * @return True if specified value is a match, False otherwise
     */
    @Override
    public boolean isMatch(Object otherValue) {
        if (!(otherValue instanceof String)) {
            return false;
        }

        String other = (String) otherValue;

        return this.getValue().trim().equalsIgnoreCase(other.trim());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;
        return this.getValue().equals(otherTag.getValue());
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return this.getValue();
    }

}
