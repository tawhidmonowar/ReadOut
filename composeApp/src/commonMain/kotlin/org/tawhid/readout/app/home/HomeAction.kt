package org.tawhid.readout.app.home


sealed interface HomeAction {
    data object OnShowAboutInfoDialog : HomeAction
    data object OnHideAboutInfoDialog : HomeAction

    data object OnSummarizeClick : HomeAction
    data object OnSettingClick : HomeAction

}