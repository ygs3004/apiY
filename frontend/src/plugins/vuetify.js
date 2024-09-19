import {createVuetify} from "vuetify";
import {md2} from "vuetify/blueprints";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import 'vuetify/styles'
import '@mdi/font/css/materialdesignicons.css'
import {aliases, mdi} from "vuetify/iconsets/mdi";


export default createVuetify({
    // aliases: {
    //     MyButton: VBtn
    // },
    defaults: {
        // MyButton: {variant: 'tonal'}
        VTextField: {
            variant: "solo"
        },
        VTextarea: {
            variant: "solo"
        },
        VSelect: {
            variant: "solo",
            itemTitle: "label",
            itemValue: "value",
        }
    },
    blueprint: md2,
    components,
    directives,
    theme: {
        themes: {
            light: {
                colors: {
                    primary: '#0066FF',
                    secondary: '#eefaf8',
                    accent: '#0044CC',
                    error: '#fc7474',
                    info: '#FFD95A',
                    success: '#4CAF50',
                    warning: '#FFC107',
                    pastel: '#dbe8ff'
                }
            }
        }
    },
    icons: {
        defaultSet: 'mdi',
        aliases,
        sets: {
            mdi,
        },
    },
})
