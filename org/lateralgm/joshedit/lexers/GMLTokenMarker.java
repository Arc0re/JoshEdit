/**
 * @file GMLTokenMarker.java
 * @brief Class implementing a GML lexer for syntax highlighting.
 *
 * @section License
 *
 *          Copyright (C) 2011 Josh Ventura <JoshV10@gmail.com>
 *          Copyright (C) 2013-2014 Robert B. Colton
 *          This file is a part of the LateralGM IDE.
 *
 *          This program is free software: you can redistribute it and/or modify
 *          it under the terms of the GNU General Public License as published by
 *          the Free Software Foundation, either version 3 of the License, or
 *          (at your option) any later version.
 *
 *          This program is distributed in the hope that it will be useful,
 *          but WITHOUT ANY WARRANTY; without even the implied warranty of
 *          MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *          GNU General Public License for more details.
 *
 *          You should have received a copy of the GNU General Public License
 *          along with this program. If not, see <http://www.gnu.org/licenses/>.
 **/

package org.lateralgm.joshedit.lexers;

import static org.lateralgm.joshedit.ColorProfile.makeEntry;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import org.lateralgm.joshedit.ColorProfile;
import org.lateralgm.joshedit.ColorProfile.ColorProfileEntry;
import org.lateralgm.joshedit.DefaultKeywords.Constant;
import org.lateralgm.joshedit.DefaultKeywords.Construct;
import org.lateralgm.joshedit.DefaultKeywords.Function;
import org.lateralgm.joshedit.DefaultKeywords.Operator;
import org.lateralgm.joshedit.DefaultKeywords.Variable;
import org.lateralgm.joshedit.DefaultTokenMarker;

/**
 * Sample GML token marker class based on the default token marker.
 */
public class GMLTokenMarker extends DefaultTokenMarker {

  private static final String S_HEX_LITERAL = "HEX_LITERAL"; //$NON-NLS-1$
  private static final String S_NUMERIC_LITERAL = "NUMERIC_LITERAL"; //$NON-NLS-1$
  private static final String S_SINGLEQ_STRING = "SINGLEQ_STRING"; //$NON-NLS-1$
  private static final String S_DOUBLEQ_STRING = "DOUBLEQ_STRING"; //$NON-NLS-1$
  private static final String S_LINE_COMMENT = "LINE_COMMENT"; //$NON-NLS-1$
  private static final String S_DOC_LINE_COMMENT = "FORMAL_LINE_COMMENT"; //$NON-NLS-1$
  private static final String S_BLOCK_COMMENT = "BLOCK_COMMENT"; //$NON-NLS-1$
  private static final String S_DOC_COMMENT = "FORMAL_COMMENT"; //$NON-NLS-1$
  private static final String S_VARIABLES = "VARIABLES"; //$NON-NLS-1$
  private static final String S_CONSTANTS = "CONSTANTS"; //$NON-NLS-1$
  private static final String S_OPERATORS = "OPERATORS"; //$NON-NLS-1$
  private static final String S_CONSTRUCTS = "CONSTRUCTS"; //$NON-NLS-1$
  private static final String S_FUNCTIONS = "FUNCTIONS"; //$NON-NLS-1$
  private static final String S_OPS_AND_SEPS = "OPS_AND_SEPS"; //$NON-NLS-1$

  private static final Color NAVY = new Color(0, 0, 200);
  private static final Color BLUE_BLACK = new Color(0, 0, 100);
  private static final Color FAGGOTRY_BLUE = new Color(54, 116, 186);
  private static final Color LIGHT_BLUE = new Color(100, 100, 255);
  private static final Color HALFASS_TURQUOIS = new Color(0, 100, 150);
  private static final Color FOREST = new Color(13, 135, 13);
  private static final Color DARK_RED = new Color(150, 0, 0);

  private static final Color GREEN = new Color(13, 165, 13);
  private static final Color BLUE = new Color(0, 0, 255);
  private static final Color RED = new Color(255, 0, 0);
  private static final Color MAGENTA = new Color(255, 0, 255);
  private static final Color LIGHT_RED = new Color(255, 100, 100);
  private static final Color CYAN = new Color(0, 255, 255);
  private static final Color ORANGE = new Color(255, 128, 0);

  private static final ColorProfile PROFILE_ADVANCED_FAGGOTRY;
  static {
    HashMap<String, ColorProfileEntry> colors = new HashMap<String, ColorProfileEntry>();

    colors.put(S_DOC_COMMENT, makeEntry(S_DOC_COMMENT, FAGGOTRY_BLUE, Font.BOLD));
    colors.put(S_BLOCK_COMMENT, makeEntry(S_BLOCK_COMMENT, FOREST, Font.ITALIC));
    colors.put(S_DOC_LINE_COMMENT, makeEntry(S_DOC_LINE_COMMENT, FAGGOTRY_BLUE, Font.BOLD));
    colors.put(S_LINE_COMMENT, makeEntry(S_LINE_COMMENT, FOREST, Font.ITALIC));
    colors.put(S_DOUBLEQ_STRING, makeEntry(S_DOUBLEQ_STRING, BLUE_BLACK, Font.PLAIN));
    colors.put(S_SINGLEQ_STRING, makeEntry(S_SINGLEQ_STRING, BLUE_BLACK, Font.PLAIN));

    colors.put(S_FUNCTIONS, makeEntry(S_FUNCTIONS, HALFASS_TURQUOIS, Font.PLAIN));
    colors.put(S_CONSTRUCTS, makeEntry(S_CONSTRUCTS, NAVY, Font.PLAIN));
    colors.put(S_OPERATORS, makeEntry(S_OPERATORS, NAVY, Font.PLAIN));
    colors.put(S_CONSTANTS, makeEntry(S_CONSTANTS, DARK_RED, Font.PLAIN));
    colors.put(S_VARIABLES, makeEntry(S_VARIABLES, BLUE_BLACK, Font.ITALIC));

    colors.put(S_OPS_AND_SEPS, makeEntry(S_OPS_AND_SEPS, NAVY, Font.PLAIN));
    colors.put(S_NUMERIC_LITERAL, makeEntry(S_NUMERIC_LITERAL, DARK_RED, Font.PLAIN));
    colors.put(S_HEX_LITERAL, makeEntry(S_HEX_LITERAL, LIGHT_BLUE, Font.PLAIN));

    PROFILE_ADVANCED_FAGGOTRY = new ColorProfile("Advanced Faggotry", colors); //$NON-NLS-1$
  }

  private static final ColorProfile PROFILE_CODE_BLOCKS;
  static {
    HashMap<String, ColorProfileEntry> colors = new HashMap<String, ColorProfileEntry>();

    colors.put(S_DOC_COMMENT, makeEntry(S_DOC_COMMENT, LIGHT_BLUE, Font.BOLD));
    colors.put(S_BLOCK_COMMENT, makeEntry(S_BLOCK_COMMENT, GREEN, Font.ITALIC));
    colors.put(S_DOC_LINE_COMMENT, makeEntry(S_DOC_LINE_COMMENT, LIGHT_BLUE, Font.BOLD));
    colors.put(S_LINE_COMMENT, makeEntry(S_LINE_COMMENT, GREEN, Font.ITALIC));
    colors.put(S_DOUBLEQ_STRING, makeEntry(S_DOUBLEQ_STRING, BLUE, Font.PLAIN));
    colors.put(S_SINGLEQ_STRING, makeEntry(S_SINGLEQ_STRING, BLUE, Font.PLAIN));

    colors.put(S_FUNCTIONS, makeEntry(S_FUNCTIONS, NAVY, Font.PLAIN));
    colors.put(S_CONSTRUCTS, makeEntry(S_CONSTRUCTS, NAVY, Font.BOLD));
    colors.put(S_OPERATORS, makeEntry(S_OPERATORS, NAVY, Font.BOLD));
    colors.put(S_CONSTANTS, makeEntry(S_CONSTANTS, DARK_RED, Font.PLAIN));
    colors.put(S_VARIABLES, makeEntry(S_VARIABLES, BLUE, Font.ITALIC));

    colors.put(S_OPS_AND_SEPS, makeEntry(S_OPS_AND_SEPS, RED, Font.PLAIN));
    colors.put(S_NUMERIC_LITERAL, makeEntry(S_NUMERIC_LITERAL, MAGENTA, Font.PLAIN));
    colors.put(S_HEX_LITERAL, makeEntry(S_HEX_LITERAL, LIGHT_RED, Font.PLAIN));

    PROFILE_CODE_BLOCKS = new ColorProfile("Code::Blocks", colors); //$NON-NLS-1$
  }

  private final ColorProfile profile = PROFILE_ADVANCED_FAGGOTRY;

  /** Collection of all built-in profiles */
  @Override
  public Collection<ColorProfile> defaultProfiles() {
    return Arrays.asList(new ColorProfile[] { PROFILE_CODE_BLOCKS, PROFILE_ADVANCED_FAGGOTRY });
  }

  static KeywordSet resNames, scrNames, constructs, functions, operators, constants, variables;

  /** Construct, populating language data. */
  public GMLTokenMarker() {
    super();
    schemes.add(new BlockDescriptor(S_DOC_COMMENT, "/\\*(?=\\*)", "\\*/", profile)); //$NON-NLS-1$ //$NON-NLS-2$
    schemes.add(new BlockDescriptor(S_BLOCK_COMMENT, "/(?=\\*)", "\\*/", profile)); //$NON-NLS-1$ //$NON-NLS-2$
    schemes.add(new BlockDescriptor(S_DOC_LINE_COMMENT, "///", "$", profile)); //$NON-NLS-1$ //$NON-NLS-2$
    schemes.add(new BlockDescriptor(S_LINE_COMMENT, "//", "$", profile)); //$NON-NLS-1$ //$NON-NLS-2$
    schemes.add(new BlockDescriptor(S_DOUBLEQ_STRING, "\"", "\"", profile)); //$NON-NLS-1$ //$NON-NLS-2$
    schemes.add(new BlockDescriptor(S_SINGLEQ_STRING, "'", "'", profile)); //$NON-NLS-1$ //$NON-NLS-2$

    functions = addKeywordSet(S_FUNCTIONS, profile);
    for (Function f : GMLKeywords.FUNCTIONS) {
      Collections.addAll(functions.words, f.getName());
    }
    constructs = addKeywordSet(S_CONSTRUCTS, profile);
    for (Construct c : GMLKeywords.CONSTRUCTS) {
      Collections.addAll(constructs.words, c.getName());
    }
    operators = addKeywordSet(S_OPERATORS, profile);
    for (Operator o : GMLKeywords.OPERATORS) {
      Collections.addAll(operators.words, o.getName());
    }
    constants = addKeywordSet(S_CONSTANTS, profile);
    for (Constant c : GMLKeywords.CONSTANTS) {
      Collections.addAll(constants.words, c.getName());
    }
    variables = addKeywordSet(S_VARIABLES, profile);
    for (Variable v : GMLKeywords.VARIABLES) {
      Collections.addAll(variables.words, v.getName());
    }

    tmKeywords.add(functions);
    tmKeywords.add(constructs);
    tmKeywords.add(operators);
    tmKeywords.add(constants);
    tmKeywords.add(variables);

    CharSymbolSet css = new CharSymbolSet(S_OPS_AND_SEPS, profile);
    char[] ca = "{[()]}!%^&*-/+=?:~<>.,;".toCharArray(); //$NON-NLS-1$
    for (int i = 0; i < ca.length; i++) {
      css.chars.add(ca[i]);
    }
    tmChars.add(css);

    otherTokens.add(new SimpleToken(S_NUMERIC_LITERAL, "[0-9]+", profile)); //$NON-NLS-1$
    otherTokens.add(new SimpleToken(S_HEX_LITERAL, "\\$[0-9A-Fa-f]+", profile)); //$NON-NLS-1$
  }
}
