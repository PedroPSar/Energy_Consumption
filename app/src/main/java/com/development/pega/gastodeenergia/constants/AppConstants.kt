package com.development.pega.gastodeenergia.constants

class AppConstants private constructor() {
    object OBJECT_DATABASE {
        const val TABLE_NAME = "electronicObjects"

        object COLUMNS {
            const val ID = "id"
            const val NAME = "name"
            const val WATTS = "watts"
            const val USED_HOURS = "usedHours"
        }
    }

    companion object {
        const val OBJECT_ID = "obj_id"
        const val ENERGY_TARIFF_KEY = "energy_tariff"
        const val ENERGY_TARIFF_DEFAULT_VALUE = 0.0f
    }
}