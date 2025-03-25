<template>
    <div class="bg-class section-class h-150 w-120">
        <v-container class="h-100">
            <div class="py-16 mt-14 d-flex justify-center">
                <v-card height="auto" width="600" rounded="xl">
                    <v-form v-model="form">
                        <div class="px-4 py-6">
                            <div class="d-flex justify-center">
                                <a href="/">
                                    <v-img src="#logo" :width="smAndUp ? 140 : 150" height="100" aspect-ratio="16/9" contain
                                        alt="Logo_sorsogon"></v-img>
                                </a>
                            </div>
                            <v-text-field v-model="email" label="Email" placeholder="Type your Email Address" flat
                                variant="outlined" rounded="lg" id="email" class="mt-3" :rules="[v =>
                                    !v || /^[\w+.-]*@[\w+.-]*\.[\w]{2,3}$/.test(v) || 'Please enter a valid email']"
                                hide-details="auto"></v-text-field>
                            <v-text-field type="password" v-model="password" label="Password"
                                placeholder="Type your Password" flat variant="outlined" rounded="lg" id="firstName"
                                class="mt-3" :rules="[v => !!v || 'This field cannot be empty.']" hide-details="auto"
                                @keyup.enter="login"></v-text-field>
                            <div class="d-flex justify-center pt-6">
                                <v-btn width="200" height="50" size="x-large"
                                    class="text-body-2 font-weight-bold bg-red" variant="outlined" color="white"
                                    @click="login">LOGIN</v-btn>
                            </div>
                        </div>
                    </v-form>
                </v-card>
            </div>
        </v-container>
    </div>
</template>

<script>
import { useDisplay } from "vuetify";
import logo from "../../../assets/NewLogo.png";
import backgroundImage from "/resources/assets/bgMain.png";

export default {
    data() {
        return {
            logo,
            backgroundImage,
            form: false,
            email: "",
            password: "",
        };
    },
    computed: {
        backgroundStyle() {
            return {
                background: `linear-gradient(to right, rgba(20, 0, 255, 0.8), rgba(20, 0, 255, 0.5), rgba(20, 0, 255, 0.2), rgba(20, 0, 255, 0)), url(${this.backgroundImage})`,
                backgroundPosition: "center center",
                backgroundSize: "cover",
            };
        },
    },
    setup() {
        const { smAndUp } = useDisplay();
        return {
            smAndUp,
        };
    },
    methods: {
        login() {
            // Temporary redirection to the dashboard without login logic
            this.$router.push('/dashboard');
        }

            // async login() {
        //     try {
        //         const response = await axios.post('/api/login', {
        //             email: this.email,
        //             password: this.password
        //         });

        //         const token = response.data.access_token;
        //         const user = response.data.user;

        //         if (!token) {
        //             throw new Error('No token received.');
        //         }
        //         const profileStore = Profile();
        //         profileStore.saveData(token, user.email, user.first_name, user.last_name, user.role);
        //         axios.defaults.headers.common['Authorization'] = Bearer ${token};
        //         this.$router.push('/dashboard');
        //     } catch (error) {
        //         console.error(error.response?.data?.error || error.message);
        //         alert('Login failed. Please check your credentials.');
        //     }
        // }
    }
};
</script>

<style lang="scss" scoped>
$xm-breakpoint: 600px;

.section-class {
    height: 100vh;
    position: relative;
    background-color: red;

    .bg-class {
        background-size: cover !important;
        background-repeat: no-repeat !important;

        @media screen and (max-width: $xm-breakpoint) {
            background-size: cover !important;
        }
    }

    @media screen and (max-width: $xm-breakpoint) {
        height: 370px;
    }
}
</style>
