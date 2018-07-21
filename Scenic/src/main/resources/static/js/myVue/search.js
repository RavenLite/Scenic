new Vue({
    el: '#app',
    data: {
        gridData: [],
        temp:"",
        apiUrl: 'http://localhost:8080/search',
        form:{
            input1: null
        }
    },
    methods: {
        submit(){
            console.log(this.form.input1);
            this.$http.get(this.apiUrl,{params: {search: this.form.input1}}).then((response) => {
                //response = response.json(),
                this.temp = response,
                console.log(response),
                
                this.gridData = response.body,
                console.log(this.gridData)
            })
        }
    }
});