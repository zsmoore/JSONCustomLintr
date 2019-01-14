package report;

import j2html.tags.Tag;

import static j2html.TagCreator.*;

class FooterSection {

    Tag getFooter() {
        return footer(
            p(text("Created with: "), a("JSONCustomLintr").withHref("https://github.com/zsmoore/JSONCustomLintr/")),
            p(text("Created by Zachary Moore: "), a("Personal Website").withHref("https://www.zachary-moore.com")),
            p(text("Personal Email: "), a("mailto:zsmoore@zachary-moore.com").withHref("mailto:zsmoore@zachary-moore.com"))
        );
    }
}
