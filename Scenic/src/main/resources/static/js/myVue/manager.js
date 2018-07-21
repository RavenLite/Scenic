var vm = new Vue({
    el: '#app1',
    data: {
        gridData: [],
        temp:"",
        apiUrl1: 'http://localhost:8080/addNode',
        apiUrl2: 'http://localhost:8080/listallnodes',
        apiUrl3: 'http://localhost:8080/removeNode',
        form:{
            name: null,
            des: null,
            welcome: null,
            rest: null,
            toilet: null,
            name2: null
        }
    },
    mounted: function () {
        this.getNodes()
    },
    methods: {
        submit1(){
            this.$http.get(this.apiUrl1,{params: {name: this.form.name, des: this.form.des, welcome: this.form.welcome, rest: this.form.rest, toilet: this.form.toilet}}).then((response) => {
            });
            this.$http.get(this.apiUrl2).then((response) => {
                this.gridData = response.body
        });
        },
        submit2(){
            this.$http.get(this.apiUrl3,{params: {name: this.form.name}}).then((response) => {
            });
            this.getNodes();
        },
        getNodes(){
            console.log("Start getting data");
            this.$http.get(this.apiUrl2).then((response) => {
                this.gridData = response.body
        })
    }}
});