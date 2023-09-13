package com.health.pressure.dao

import com.health.pressure.R
import com.health.pressure.ext.colorValue
import com.health.pressure.ext.stringValue

sealed class PressureState {

    abstract val stateName: String
    abstract val stateColor: Int
    abstract val stateContent: String
    abstract val stateExtra: String
    abstract val sliderBis: Float

    object Hypotension : PressureState() {
        override val stateName: String get() = R.string.text_hypotension.stringValue
        override val stateColor: Int get() = R.color.color_0599eb.colorValue
        override val stateContent: String get() = R.string.text_hypotension_content.stringValue
        override val stateExtra: String get() = R.string.text_hypotension_extra.stringValue
        override val sliderBis: Float = 0.065F
    }

    object Normal : PressureState() {
        override val stateName: String get() = R.string.text_normal.stringValue
        override val stateColor: Int get() = R.color.color_55d147.colorValue
        override val stateContent: String get() = R.string.text_normal_content.stringValue
        override val stateExtra: String get() = R.string.text_normal_extra.stringValue
        override val sliderBis: Float = 0.24F
    }

    object Elevated : PressureState() {
        override val stateName: String get() = R.string.text_elevated.stringValue
        override val stateColor: Int get() = R.color.color_fed236.colorValue
        override val stateContent: String get() = R.string.text_elevated_content.stringValue
        override val stateExtra: String get() = R.string.text_elevated_extra.stringValue
        override val sliderBis: Float = 0.415F
    }

    object HypertensionStageFirst : PressureState() {
        override val stateName: String get() = R.string.text_hypertension_stage_1.stringValue
        override val stateColor: Int get() = R.color.color_ffb968.colorValue
        override val stateContent: String get() = R.string.text_hypertension_stage_1_content.stringValue
        override val stateExtra: String get() = R.string.text_hypertension_stage_1_extra.stringValue
        override val sliderBis: Float = 0.59F
    }

    object HypertensionStageSecond : PressureState() {
        override val stateName: String get() = R.string.text_hypertension_stage_2.stringValue
        override val stateColor: Int get() = R.color.color_ff8767.colorValue
        override val stateContent: String get() = R.string.text_hypertension_stage_2_content.stringValue
        override val stateExtra: String get() = R.string.text_hypertension_stage_2_extra.stringValue
        override val sliderBis: Float = 0.765F
    }

    object Hypertensive : PressureState() {
        override val stateName: String get() = R.string.text_hypertensive.stringValue
        override val stateColor: Int get() = R.color.color_fd5f55.colorValue
        override val stateContent: String get() = R.string.text_hypertensive_content.stringValue
        override val stateExtra: String get() = R.string.text_hypertensive_extra.stringValue
        override val sliderBis: Float = 0.94F
    }

}
