package org.moChiThirst.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Color {
    private static final Pattern hexPattern = Pattern.compile("&#[a-fA-F0-9]{6}");
    private static final Pattern gradientPattern = Pattern.compile("&(#[a-fA-F0-9]{6}(?:-#[a-fA-F0-9]{6})+)((?:&[k-oK-OrR])+)?(.*?)(?=(&#[a-fA-F0-9]{6}|&[a-fA-Fk-oK-OrR0-9])|$)");

    public static String translate(String input) {
        String translatedGradient;
        for(Matcher gradientMatch = gradientPattern.matcher(input);
            gradientMatch.find();
            input = input.replace(gradientMatch.group(), translatedGradient)
        ) {
            String[] hexGroup = gradientMatch.group(1).split("-");
            String style = gradientMatch.group(2);
            String text = gradientMatch.group(3);
            translatedGradient = gradientTranslate(text, style, hexGroup);
        }

        String color;
        for(Matcher hexMatch = hexPattern.matcher(input); hexMatch.find(); input = input.replace("&" + color, ChatColor.of(color) + "")) {
            color = input.substring(hexMatch.start() + 1, hexMatch.end());
        }

        return input.replace("&", "§");
    }

    private static String gradientTranslate(String input, String style, String[] hexGroup) {
        int textLength = input.length();
        int hexCount = hexGroup.length;
        StringBuilder gradientString = new StringBuilder();
        if (textLength <= hexCount) {
            for(int a = 0; a < textLength; ++a) {
                String hexColor = a == 0 ? hexGroup[0] : hexGroup[a];
                gradientString.append(ChatColor.of(hexColor)).append(style != null ? style : "").append(input.charAt(a));
            }
        } else {
            int segmentCount = hexGroup.length - 1;
            int segmentCharCount = (int)((double)((textLength - hexCount) / segmentCount));
            int remainingChars = (textLength - hexCount) % segmentCount;
            int[] segmentCharCountList = new int[segmentCount];
            Arrays.fill(segmentCharCountList, segmentCharCount);

            for(int b = 0; b < remainingChars; ++b) {
                int var10002 = segmentCharCountList[b]++;
            }

            int charIndex = 0;

            for(int c = 0; c < segmentCount; ++c) {
                int r1 = Integer.parseInt(hexGroup[c].substring(1, 3), 16);
                int g1 = Integer.parseInt(hexGroup[c].substring(3, 5), 16);
                int b1 = Integer.parseInt(hexGroup[c].substring(5, 7), 16);
                int r2 = Integer.parseInt(hexGroup[c + 1].substring(1, 3), 16);
                int g2 = Integer.parseInt(hexGroup[c + 1].substring(3, 5), 16);
                int b2 = Integer.parseInt(hexGroup[c + 1].substring(5, 7), 16);
                gradientString.append(ChatColor.of(hexGroup[c])).append(style != null ? style : "").append(input.charAt(charIndex++));

                for(int d = 1; d < segmentCharCountList[c] + 1; ++d) {
                    float ratio = (float)d / (float)segmentCharCountList[c];
                    int r = (int)((float)r1 + ratio * (float)(r2 - r1));
                    int g = (int)((float)g1 + ratio * (float)(g2 - g1));
                    int b = (int)((float)b1 + ratio * (float)(b2 - b1));
                    String hexColor = String.format("#%02x%02x%02x", r, g, b);
                    gradientString.append(ChatColor.of(hexColor)).append(style != null ? style : "").append(input.charAt(charIndex));
                    ++charIndex;
                }
            }

            gradientString.append(ChatColor.of(hexGroup[hexGroup.length - 1])).append(style != null ? style : "").append(input.charAt(textLength - 1));
        }

        return gradientString.toString();
    }
}