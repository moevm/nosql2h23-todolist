<template>
    <v-container fluid class="fill-height">
        <v-row>
            <v-col class="text-center">
                <v-img
                    src="../assets/error-404.png"
                    alt="image 404"
                    max-height="400px"
                    contain
                />
                <h2><b>404</b> Page not found</h2>
                <p>Visit the home page <br> You might find it</p>
                <v-btn
                    :color="buttonColor"
                    :to="{ name: 'home' }"
                    link
                >
                    Home
                </v-btn>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    name: "NotFound",
    computed: {
        buttonColor(){
            return this.$vuetify.theme.dark ? 'deep-purple darken-1' : 'deep-purple lighten-4';
        }
    }
}
</script>
