new Vue({
    el: '#app',
    data: {
        gridData: [],
        msg:"",
        apiUrl1: 'http://localhost:8080/enterstack',
        apiUrl2: 'http://localhost:8080/exitstack',
        form:{
            enterNum: "",
            enterTime: "",
            exitNum: "",
            exitTime:""
        }
    },
    methods: {
        submit1(){
            this.$http.get(this.apiUrl1,{params: {num: this.form.enterNum, arvTime: this.form.enterTime}}).then((response) => {
                console.log(response);
                this.msg+=response.bodyText;
            });
            //this.getNodes();
        },
        submit2(){
            this.$http.get(this.apiUrl2,{params: {num: this.form.exitNum, levTime: this.form.exitTime}}).then((response) => {
                console.log(response);
                this.msg+=response.bodyText;
            });
            //this.getNodes();
        }
    }
});