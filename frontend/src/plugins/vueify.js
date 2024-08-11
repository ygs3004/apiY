import {createVuetify} from "vuetify";
import {md3} from "vuetify/blueprints";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import 'vuetify/styles'
import '@mdi/font/css/materialdesignicons.css'

export default createVuetify({
    blueprint: md3,
    components,
    directives,
    theme: {
        themes: {
            light: {
                colors: {
                    primary: '#1976D2',
                    secondary: '#ffe996',
                    accent: '#82B1FF',
                    error: '#FF5252',
                    info: '#2196F3',
                    success: '#4CAF50',
                    warning: '#FFC107'
                }
            }
        }
    }
})