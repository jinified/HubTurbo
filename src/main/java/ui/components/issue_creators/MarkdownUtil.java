package ui.components.issue_creators;

import org.pegdown.PegDownProcessor;

public class MarkdownUtil {
    
    public static void main(String[] args) {
        String md = "## Hello \n### I am a god\n- babu\n- bibi";
        String checkbox = "- [ ] hello\n - [ ] bibi";
        PegDownProcessor mdProcessor = new PegDownProcessor();
        String html = mdProcessor.markdownToHtml(checkbox);
        System.out.println(html);
    }

}
