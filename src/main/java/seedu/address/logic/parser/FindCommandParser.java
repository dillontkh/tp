package seedu.address.logic.parser;

import static seedu.address.logic.messages.FindCommandMessages.MESSAGE_INVALID_COMMAND_FORMAT_FIND;
import static seedu.address.logic.parser.CliSyntax.ALL_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIXES_NAME_PHONE_EMAIL_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.predicates.AddressContainsSubstringPredicate;
import seedu.address.model.person.predicates.CombinedPredicates;
import seedu.address.model.person.predicates.EmailContainsSubstringPredicate;
import seedu.address.model.person.predicates.HeightContainsRangePredicate;
import seedu.address.model.person.predicates.NameContainsSubstringPredicate;
import seedu.address.model.person.predicates.NoteContainsSubstringPredicate;
import seedu.address.model.person.predicates.PhoneContainsSubstringPredicate;
import seedu.address.model.person.predicates.TagSetContainsAllTagsPredicate;
import seedu.address.model.person.predicates.WeightMapContainsWeightRangePredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT_FIND);
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, ALL_PREFIXES);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIXES_NAME_PHONE_EMAIL_ADDRESS);

        NameContainsSubstringPredicate namePredicate = new NameContainsSubstringPredicate(
                ParserUtil.parseSearchString(argMultimap.getValueOrPreamble(PREFIX_NAME)));
        PhoneContainsSubstringPredicate phonePredicate = new PhoneContainsSubstringPredicate(
                ParserUtil.parseSearchString(argMultimap.getValueOrEmpty(PREFIX_PHONE)));
        EmailContainsSubstringPredicate emailPredicate = new EmailContainsSubstringPredicate(
                ParserUtil.parseSearchString(argMultimap.getValueOrEmpty(PREFIX_EMAIL)));
        AddressContainsSubstringPredicate addressPredicate = new AddressContainsSubstringPredicate(
                ParserUtil.parseSearchString(argMultimap.getValueOrEmpty(PREFIX_ADDRESS)));
        WeightMapContainsWeightRangePredicate weightPredicate = new WeightMapContainsWeightRangePredicate(
                ParserUtil.parseSearchRange(argMultimap.getValue(PREFIX_WEIGHT)));
        HeightContainsRangePredicate heightPredicate = new HeightContainsRangePredicate(
                ParserUtil.parseSearchRange(argMultimap.getValue(PREFIX_HEIGHT)));
        NoteContainsSubstringPredicate notePredicate = new NoteContainsSubstringPredicate(ParserUtil
                .parseSearchString(argMultimap.getValueOrEmpty(PREFIX_NOTE)));
        TagSetContainsAllTagsPredicate tagsPredicate = new TagSetContainsAllTagsPredicate(
                ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG)));

        CombinedPredicates predicates = new CombinedPredicates(namePredicate, phonePredicate, emailPredicate,
                addressPredicate, weightPredicate, heightPredicate, notePredicate, tagsPredicate);

        return new FindCommand(predicates);
    }
}
