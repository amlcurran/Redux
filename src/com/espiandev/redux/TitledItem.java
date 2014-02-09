package com.espiandev.redux;

public interface TitledItem {
    CharSequence getHostedSubtitle(ResourceStringProvider stringProvider);

    CharSequence getHostedTitle(ResourceStringProvider stringProvider);
}
