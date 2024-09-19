<script setup>
import {getCurrentInstance, onMounted, ref} from "vue";

const {proxy} = getCurrentInstance();
const {$axios} = proxy;

const weatherForecast = ref([{
  forecastDatetime: "",
  precipitationProbability: "",
  precipitationType: "",
  skyCondition: "",
  humidity: "",
  temperature: "",
  windDirection: "",
  windSpeed: ""
}]);

const searchWeatherForecast = () => {
  $axios.get("/weather/forecast")
      .then(response => {
        const result = response.data
        weatherForecast.value = result.reverse().map( weather => {
          const datetime = weather.forecastDatetime.split(" ");
          let icon = "";
          switch (weather.skyCondition){
            case "맑음" :
              icon = "mdi-weather-sunny";
              break
            case "구름많음" :
              icon = "mdi-weather-partly-cloudy";
              break
            case "흐림" :
              icon = "mdi-weather-cloudy";
              break
          }

          switch (weather.precipitationType) {
            case "비" :
              icon = "mdi-weather-rainy";
              break
            case "비/눈" :
              icon = "mdi-weather-snowy-rainy";
              break
            case "눈" :
              icon = "mdi-weather-snowy";
              break
            case "소나기" :
              icon = "mdi-weather-pouring";
              break
          }

          return {
            ...weather,
            forecastDate: datetime[0],
            forecastDay: datetime[1],
            forecastTime: datetime[2],
            icon,
          }
        });
      });
}

onMounted(() => {
  searchWeatherForecast();
})
</script>

<template>
    <VCol v-for="weather in weatherForecast" :key="weather.forecastDatetime" cols="12" lg="3">
      <VCard variant="elevated" color="secondary">
        <VCardItem style="height:100%;">
          <VCardTitle>
            <div class="py-2 pr-4 d-flex align-center" style="gap:2rem">
              <div>{{ weather.skyCondition || '정보수집중입니다.' }} {{ weather.precipitationType }}</div>
              <div>
                <VIcon :icon="weather.icon" size="40"/>
              </div>
            </div>
          </VCardTitle>
          <VCardSubtitle opacity="0.7" >
            <div class="font-weight-bold text-left">{{ weather.forecastDatetime}}</div>
          </VCardSubtitle>
          <VCardText>
            <div class="d-flex" style="gap:1rem">
              <div>강수확률: {{ weather.precipitationProbability }}%</div>

            </div>
            <div class="d-flex mt-3" style="gap:1rem">
              <div>습도 {{ weather.humidity }}%</div>
              <div>온도 {{ weather.temperature }}℃</div>
              <div>풍향 {{ weather.windDirection }}°</div>
              <div>풍속 {{ weather.windSpeed }}°</div>
            </div>
          </VCardText>
        </VCardItem>
      </VCard>
    </VCol>
</template>

<style scoped>
</style>