package com.espiandev.redux;

/**
 * Represents an item which can show titles and subtitles
 */
public interface TitleHost extends ResourceStringProvider {
    void setTitle(CharSequence title);
    void setSubtitle(CharSequence subtitle);

    public static final TitleHost NONE = new TitleHost() {
        @Override
        public void setTitle(CharSequence title) {

        }

        @Override
        public void setSubtitle(CharSequence subtitle) {

        }

        @Override
        public String getString(int id) {
            return null;
        }

        @Override
        public String getString(int id, Object... args) {
            return null;
        }
    };

}
