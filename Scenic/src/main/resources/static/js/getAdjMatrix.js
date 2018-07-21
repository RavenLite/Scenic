new Vue({
    el: '#arcTable',
    data: {
        matrix: [],
        nodeset: [],
        temp:"",
        apiUrl1: 'http://localhost:8080/getAdjMatrix',
        apiUrl2: 'http://localhost:8080/getNodeSet'
    },
    
    mounted: function () {
        this.getMatrix(),
        this.getNodes()
    },
    methods: {
        getMatrix: function () {
            console.log("Start getting data1");
            this.$http.get(this.apiUrl1).then((response) => {
                //response = response.json(),
                this.temp = response,
                console.log(response),
                
                this.gridData = response.body,
                console.log(this.gridData)
            })
        },
        getNodes: function () {
            console.log("Start getting data2");
            this.$http.get(this.apiUrl2).then((response) => {
                //response = response.json(),
                this.temp = response,
                console.log(response),
                
                this.gridData = response.body,
                console.log(this.gridData)
            })
        }
    }
});