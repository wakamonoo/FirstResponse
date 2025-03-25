<template>
    <v-app>
        <v-main>
            <LoginForm />
        </v-main>
        <!-- <MainFooter /> -->
    </v-app>
</template>

<script>
import LoginForm from '@/components/Login/LoginForm.vue';
import MainFooter from "@/layouts/Default/MainFooter.vue";
export default {
    components: {
        LoginForm,
        MainFooter
    }
};
</script>
