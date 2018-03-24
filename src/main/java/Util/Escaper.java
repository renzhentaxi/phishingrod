package Util;

public class Escaper
{
    private static char[] regexEscape = {'[', ']', '{', '}', '(', ')'};

    public static String escapeRegex(String pattern)
    {
        StringBuilder sb = new StringBuilder();
        for (char c : pattern.toCharArray())
        {
            for (char e : regexEscape)
            {
                if (c == e)
                {
                    sb.append("\\");
                    break;
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
