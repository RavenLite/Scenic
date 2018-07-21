var vm = new Vue({
    el: '#app1',
    data: {
        gridData: [],
        temp:"",
        apiUrl1: 'http://localhost:8080/addEdge',
        apiUrl2: 'http://localhost:8080/listalledges',
        apiUrl3: 'http://localhost:8080/removeEdge',
        form:{
            name1: null,
            name2: null,
            dis: null,
            name3: null,
            name4: null
        }
    },
    mounted: function () {
        this.getEdges()
    },
    methods: {
        submit1(){
            this.$http.get(this.apiUrl1,{params: {name1: this.form.name1, name2: this.form.name2, dis: this.form.dis}}).then((response) => {
            });
            this.getEdges();
        },
        submit2(){
            this.$http.get(this.apiUrl3,{params: {name3: this.form.name3, name4: this.form.name4}}).then((response) => {
            });
            this.getEdges();
        },
        getEdges: function () {
            console.log("Start getting edge data");
            this.$http.get(this.apiUrl2).then((response) => {
                console.log(response),
                
                this.gridData = response.body,
                console.log(this.gridData)
            })
    }}
});