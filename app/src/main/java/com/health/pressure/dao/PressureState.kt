package com.health.pressure.dao

import com.health.pressure.R
import com.health.pressure.ext.colorValue
import com.health.pressure.ext.stringValue

sealed class PressureState {

    abstract val stateName: String
    abstract val stateColor: Int
    abstract val stateContent: String

    object Hypotension : PressureState() {
        override val stateName: String get() = R.string.text_hypotension.stringValue
        override val stateColor: Int get() = R.color.color_0599eb.colorValue
        override val stateContent: String get() = R.string.text_hypotension_content.stringValue
    }

    object Normal : PressureState() {
        override val stateName: String get() = R.string.text_normal.stringValue
        override val stateColor: Int get() = R.color.color_55d147.colorValue
        override val stateContent: String get() = R.string.text_normal_content.stringValue
    }

    object Elevated : PressureState() {
        override val stateName: String get() = R.string.text_elevated.stringValue
        override val stateColor: Int get() = R.color.color_fed236.colorValue
        override val stateContent: String get() = R.string.text_elevated_content.stringValue
    }

    object HypertensionStageFirst : PressureState() {
        override val stateName: String get() = R.string.text_hypertension_stage_1.stringValue
        override val stateColor: Int get() = R.color.color_ffb968.colorValue
        override val stateContent: String get() = R.string.text_hypertension_stage_1_content.stringValue
    }

    object HypertensionStageSecond : PressureState() {
        override val stateName: String get() = R.string.text_hypertension_stage_2.stringValue
        override val stateColor: Int get() = R.color.color_ff8767.colorValue
        override val stateContent: String get() = R.string.text_hypertension_stage_2_content.stringValue
    }

    object Hypertensive : PressureState() {
        override val stateName: String get() = R.string.text_hypertensive.stringValue
        override val stateColor: Int get() = R.color.color_fd5f55.colorValue
        override val stateContent: String get() = R.string.text_hypertensive_content.stringValue
    }

}
