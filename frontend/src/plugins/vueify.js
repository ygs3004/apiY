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
                    primary: '#4C3D3D',
                    secondary: '#FFF7D4',
                    accent: '#C07F00',
                    error: '#FF5252',
                    info: '#FFD95A',
                    success: '#4CAF50',
                    warning: '#FFC107'
                }
            }
        }
    }
})