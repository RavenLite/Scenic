new Vue({
    el: '#app',
    data: {
        gridData: "",
        apiUrl: 'http://localhost:8080/findShortest',
        form:{
            name1: null,
            name2: null
        }
    },
    methods: {
        submit(){
            console.log(this.form.input1);
            this.$http.get(this.apiUrl,{params: {name1: this.form.name1, name2: this.form.name2}}).then((response) => {
                //response = response.json(),
                this.temp = response,
                console.log(response),
                
                this.gridData = response.body,
                console.log(this.gridData)
            })
        }
    }
});