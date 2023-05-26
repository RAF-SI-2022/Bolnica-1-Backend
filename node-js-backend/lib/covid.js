const axios = require('axios');
const caching = require('./cache')

const covidAPI = axios.create({
  baseURL: 'https://covid-api.com/api',
});

async function getReport() {

    let cache = await caching.getCache('serbia')
    if(cache){
      console.log("Found cache!");
      return cache;
    }else console.log("Cache not found!");
    const response = await covidAPI.get('/reports?q=serbia');
    caching.setCache('serbia', response.data);
    return response.data;
}

module.exports = {
  getReport
};