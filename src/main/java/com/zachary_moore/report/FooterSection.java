package com.zachary_moore.report;

import j2html.tags.Tag;

import static j2html.TagCreator.*;

class FooterSection {

    Tag getFooter() {
        return footer(
                div(
                    p(text("Created with: "), a("JSONCustomLintr").withHref("https://github.com/zsmoore/JSONCustomLintr/").withClass("text-body")).withClasses("text-white", "font-weight bold"),
                    p(text("Created by Zachary Moore: "), a("Personal Website").withHref("https://www.zachary-moore.com").withClass("text-body")).withClasses("text-white", "font-weight bold"),
                    p(text("Personal Email: "), a("mailto:zsmoore@zachary-moore.com").withHref("mailto:zsmoore@zachary-moore.com").withClass("text-body")).withClasses("text-white", "font-weight bold")
                ).withClass("container-fluid"))
                .withClass("bg-info");
    }
}
