import {createVuetify} from "vuetify";
import {md2} from "vuetify/blueprints";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import 'vuetify/styles'
import '@mdi/font/css/materialdesignicons.css'
import {aliases, mdi} from "vuetify/iconsets/mdi";


export default createVuetify({
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
                    error: '#FF5252',
                    info: '#FFD95A',
                    success: '#4CAF50',
                    warning: '#FFC107',
                    pastel: '#e7ebf3'
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
