package com.health.pressure.basic.bean

import java.util.*

sealed class LocalState {

    abstract val localName: String
    abstract val languageCode: String

    object English : LocalState() {
        override val localName: String get() = "English"
        override val languageCode: String get() = Locale.ENGLISH.language
    }

    object Traditional : LocalState() {
        override val localName: String get() = "繁體中文"
        override val languageCode: String get() = Locale.CHINESE.language
    }

    object Japanese : LocalState() {
        override val localName: String get() = "日本語"
        override val languageCode: String get() = Locale.JAPANESE.language
    }

    object French : LocalState() {
        override val localName: String get() = "Français"
        override val languageCode: String get() = Locale.FRENCH.language
    }

    object German : LocalState() {
        override val localName: String get() = "Deutsch"
        override val languageCode: String get() = Locale.GERMAN.language
    }

    object Korean : LocalState() {
        override val localName: String get() = "한국어"
        override val languageCode: String get() = Locale.KOREAN.language
    }

    object Spanish : LocalState() {
        override val localName: String get() = "Español"
        override val languageCode: String get() = "es"
    }

    object Turkish : LocalState() {
        override val localName: String get() = "Türkçe"
        override val languageCode: String get() = "tr"
    }

    object Polish : LocalState() {
        override val localName: String get() = "Polski"
        override val languageCode: String get() = "pl"
    }

    object Italian : LocalState() {
        override val localName: String get() = "Italiano"
        override val languageCode: String get() = Locale.ITALIAN.language
    }

    object Portuguese : LocalState() {
        override val localName: String get() = "Português"
        override val languageCode: String get() = "pt"
    }

    object Thai : LocalState() {
        override val localName: String get() = "ไทย"
        override val languageCode: String get() = "th"
    }

    object Romanian : LocalState() {
        override val localName: String get() = "Română"
        override val languageCode: String get() = "ro"
    }

    object Arabic : LocalState() {
        override val localName: String get() = "العربية"
        override val languageCode: String get() = "ar"
    }
}
