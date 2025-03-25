import { defineStore } from 'pinia'
export const Profile = defineStore('profilestate', {
    state: () => ({
        token: '',
        email: '',
        firstName: '',
        lastName: '',
        role: ''
    }),

    actions: {
        saveData(tk, em, fn, ln, rl) {
            this.token = tk;
            this.email = em;
            this.firstName = fn;
            this.lastName = ln;
            this.role = rl;

            const data = { token: tk, email: em, firstName: fn, lastName: ln, role: rl };
            window.localStorage.setItem('tk', JSON.stringify(data));
        },

        loadStorageData() {
            const storedData = window.localStorage.getItem('tk');
            if (storedData) {
                const parsedData = JSON.parse(storedData);
                this.saveData(parsedData.token, parsedData.email, parsedData.firstName, parsedData.lastName, parsedData.role);
            }
        },

        clearData() {
            this.token = '';
            this.email = '';
            this.firstName = '';
            this.lastName = '';
            this.role = '';
            window.localStorage.removeItem('tk');
        }
    }
});

// Automatically load the data when the store is created
// const profileStore = Profile();
// profileStore.loadStorageData();
