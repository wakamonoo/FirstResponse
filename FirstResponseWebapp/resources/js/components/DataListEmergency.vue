<template>
    <v-card class="pa-4">
        <div class="d-flex align-center">
            <v-row>
                <v-col cols="12">
                    <div class="d-flex justify-space-between align-center">
                        <h2 class="text-h4">List of Emergency</h2>
                    </div>
                </v-col>
            </v-row>
        </div>

        <!-- Data Table to show the paginated items -->
        <v-table id="officer-list" class="mt-4">
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Full Name</th>
                    <th>Address</th>
                    <th>Location</th>
                </tr>
            </thead>

            <tbody>
                <tr v-for="(item, index) in paginatedItems" :key="item.id">
                    <td>{{ item.username }}</td>
                    <td>{{ item.email }}</td>
                    <td>{{ item.full_name }}</td>
                    <td>{{ item.address }}</td>
                    <td>
                        <v-btn @click="openLocationModal(item)">View Location</v-btn>
                    </td>
                </tr>
            </tbody>
        </v-table>

        <div class="ma-5">
            <v-row>
                <v-col cols="11">
                    <v-pagination
                        v-model="currentPage"
                        :length="totalPages"
                        @input="handlePageChange"
                        rounded="circle"
                    ></v-pagination>
                </v-col>
            </v-row>
        </div>
    </v-card>

    <!-- View Location Modal -->
    <v-dialog v-model="showLocationModal" max-width="800px">
        <v-card>
            <v-card-title>Location of Emergency</v-card-title>
            <v-card-text>
                <div id="map" style="height: 400px"></div>
            </v-card-text>
            <v-card-actions>
                <v-btn @click="showLocationModal = false">Close</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
import axios from "axios";
import L from "leaflet"; // Import Leaflet
import 'leaflet/dist/leaflet.css';

export default {
    data() {
        return {
            emergencies: [],
            itemsPerPage: 10,
            currentPage: 1,
            showLocationModal: false,
            locationMapUrl: "",
        };
    },
    computed: {
        paginatedItems() {
            if (!this.emergencies || this.emergencies.length === 0) {
                return [];
            }
            const start = (this.currentPage - 1) * this.itemsPerPage;
            return this.emergencies.slice(start, start + this.itemsPerPage);
        },

        totalPages() {
            return Math.ceil(this.emergencies.length / this.itemsPerPage);
        },
    },
    async created() {
    try {
        const response = await axios.get(
            "https://firstresponseapp-b0184-default-rtdb.asia-southeast1.firebasedatabase.app/emergencies.json"
        );

        console.log("API Response:", response.data);

        // If response is an object, wrap it in an array to ensure consistent structure
        if (response.data && typeof response.data === "object") {
            this.emergencies = [response.data]; // Wrap the single emergency object into an array
        } else {
            this.emergencies = response.data || [];
        }

        console.log("Processed Emergencies:", this.emergencies);
    } catch (error) {
        console.error("Error fetching emergencies:", error);
    }
},

    methods: {
        handlePageChange(page) {
            this.currentPage = page;
        },

        openLocationModal(item) {
            if (item.location && typeof item.location === 'string') {
                const [lat, lon] = item.location.split(", ");
                this.showLocationModal = true;

                this.$nextTick(() => {
                    if (this.map) {
                        this.map.remove();
                    }
                    this.map = L.map("map").setView([parseFloat(lat), parseFloat(lon)], 13);
                    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
                        attribution: '&copy; OpenStreetMap contributors',
                    }).addTo(this.map);
                    L.marker([parseFloat(lat), parseFloat(lon)]).addTo(this.map).bindPopup("Emergency Location").openPopup();
                });
            } else {
                console.error("Invalid location data:", item.location);
                alert("Location data is missing or invalid.");
            }
        }
    },
};
</script>

<style scoped>
#map {
    height: 400px;
    width: 100%;
}
</style>
