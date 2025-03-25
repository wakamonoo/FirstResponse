<template>
    <v-app-bar :elevation="2">
        <v-row no-gutters>
            <v-col cols="6">
                <div class="d-flex align-center">
                    <div class="mx-6 py-2" id="top-nav-logo" data-aos="fade-up" data-aos-duration="800"
                        data-aos-delay="600">
                        <v-img width="100px" style="cursor: pointer" @click="$router.push({ name: 'home' })"></v-img>
                    </div>
                    <!-- <div class="text-h5 px-6">{{ routeString }}</div> -->
                </div>
            </v-col>
            <v-col cols="6">
                <div class="d-flex justify-end align-center" style="height: 100%">
                    <div class="d-flex justify-center align-center mr-2 icon-holder" @click="switchMode"
                        id="top-nav-theme-button">
                        <v-icon icon="mdi-white-balance-sunny" color="#000" v-if="isDark"></v-icon>
                        <v-icon icon="mdi-moon-waning-crescent" color="#000" class="moon"
                            style="transform: rotate(320deg)" v-if="!isDark"></v-icon>
                        <v-tooltip activator="parent" location="start"><span class="text-white">{{ `Use ${isDark ?
                            'Light' : 'Dark'} Theme` }}</span></v-tooltip>
                    </div>
                    <v-menu v-model="confirmShow" :close-on-content-click="false" location="bottom right">
                        <template v-slot:activator="{ props }">
                            <v-btn v-bind="props" variant="text" icon="mdi-logout" id="top-nav-logout-button"
                                @click.prevent.stop="confirmShow = true"></v-btn>
                        </template>

                        <v-card :title="`Confirm Logout`" :subtitle="`Are you sure you want to logout?`">
                            <v-card-actions class="d-flex justify-space-between">
                                <div class="d-flex align-center">
                                    <v-btn color="green" @click="logout" :loading="loadingSave">Yes</v-btn>
                                    <v-btn color="red" @click="confirmShow = false" :loading="loadingSave">No</v-btn>
                                </div>
                            </v-card-actions>
                        </v-card>
                    </v-menu>
                </div>
            </v-col>
        </v-row>
    </v-app-bar>
</template>


<script>
import { useTheme } from 'vuetify'
import { Theme } from '../stores/theme.js'
import AOS from 'aos'

export default {
    setup() {
        const theme = useTheme()
        return {
            Theme: Theme(),
            theme,
            toggleTheme: () => theme.global.name.value = theme.global.current.value.dark ? 'light' : 'dark'
        }
    },
    data() {
        return {
            isDark: true,
            confirmShow: false,
            loadingSave: false
        }
    },
    mounted() {
        const mode = localStorage.getItem('mode') || 'dark'
        this.theme.global.name.value = mode
        this.isDark = mode == 'dark' ? true : false
    },
    computed: {
        routeString() {
            return this.Navigation.stringRoute()
        }
    },
    methods: {
        switchMode() {
            this.isDark = !this.isDark
            this.Theme.toggle(this.isDark ? 'dark' : 'light')
            this.toggleTheme()
            this.$nextTick(() => {
                AOS.refresh();
            })
        },
        logout() {
            window.localStorage.removeItem('tk')
            window.localStorage.removeItem('tk_1')
            this.$router.push({ name: 'login' })
        },
    }
}
</script>

<style lang="scss" scoped>
.icon-holder {
    width: 44px;
    height: 44px;
    background: #fff;
    border-radius: 50%;
    border: 1px solid #707070;
    cursor: pointer;

    img {
        height: 20px;
        width: 20px;
    }
}
</style>
