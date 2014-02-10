EnumUriMatcher
==============

EnumUriMatcher aims to improve on Android's provided UriMatcher by allowing developers to associate URI matches with enumeration values rather than arbitrary integers.  This addresses the following issues:

  1. Using integer constants to distinguish between URI matches is not type safe and allows match identifiers from different matchers to be inadvertantly mixed.  The use of enumerations prevents that.
  2. When using integer constants, each URI that is added to a matcher needs to have a corresponding bit of code similar to this: "private static final int FOO = 0".  When using enumerations instead, the amount of boilerplate is reduced to simply adding a value to an enumeration.
  3. When using integer constants, each constant needs to have a unique (but otherwise arbitrary) value, which introduces the potential for value "collisions."  When using enumerations, an enum value's ordinal is used, eliminating the need for a developer to provide a meaningless value.
  4. Using enumerations also allows for useful compile-time warnings.  For example, suppose that an enumeration value is used in a switch statement.  If a value is added to the enumeration but a corresponding switch case isn't added, the Java compiler is able to warn developers about the potential oversight as long as they don't mask the warning by adding an empty default case (which, oddly enough, Lint recommends simply for the sake of completeness.)
