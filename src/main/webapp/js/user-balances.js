Ext.onReady(function(){
    
    Ext.QuickTips.init();

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
    //var hId = Ext.String.format(document.getElementById("houseIds").value);
    
    
    // this is the house id that came from the home owner landing page
    var params = Ext.urlDecode(location.search.substring(1));
    
    var houseId = params.houseId;
    var houseNum = params.houseNum;
    var address = params.address;
    
    
    var BillingBean = Ext.data.Record.create(
    [
	    {	name: 'reference', type: 'string'},
	    {	name: 'description', type: 'string'},
	    {	name: 'requestedDate', type: 'string'},
	    {	name: 'postedDate', type: 'string'},
	    {	name: 'amount', type: 'double'},
	    {	name: 'paidAmount', type: 'double'},
	    {	name: 'balance', type: 'double'}
    ]);


    var proxy = new Ext.data.HttpProxy({
        api: {
            read : 'user-get-outstanding-balances.action?houseId=' + houseId
        }
    });

    var reader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    },
    BillingBean);
    

    // Typical Store collecting the Proxy, Reader and Writer together.
    var store = new Ext.data.Store({
        id: 'user',
        proxy: proxy,
        reader: reader,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });

    //read the data from simple array
    store.load();
    
    Ext.data.DataProxy.addListener('exception', function(proxy, type, action, options, res) {
        Ext.Msg.show({
            title: 'ERROR',
            msg: res.message,
            icon: Ext.MessageBox.ERROR,
            buttons: Ext.Msg.OK
        });
    });


    var billingGrid = new Ext.grid.GridPanel({
        store: store,
        columns: [
        {
            header: "REFERENCE NUMBER",
            width: 170,
            sortable: true,
            dataIndex: 'reference'
        },
        {
            header: "DESCRIPTION",
            width: 170,
            sortable: true,
            dataIndex: 'description'
        },
        {
            header: "REQUESTED DATE",
            width: 170,
            sortable: true,
            dataIndex: 'requestedDate'
        },{
            header: "POSTED DATE",
            width: 170,
            sortable: true,
            dataIndex: 'postedDate'
        },
        {
            header: "AMOUNT",
            width: 170,
            sortable: true,
            dataIndex: 'amount'
        },{
            header: "PAID AMOUNT",
            width: 170,
            sortable: true,
            dataIndex: 'paidAmount'
        },{
            header: "BALANCE",
            width: 170,
            sortable: true,
            dataIndex: 'balance'
        }
        ],

        viewConfig: {
            forceFit:true
        },

        // inline buttons
        //plugins: [sreditor],
        cls:'x-panel-blue',
        width:400,
        height:300,
        frame:true,
        title:'OUTSTANDING BALANCES'
    //renderTo: document.body
    });

   

    var myTabPanel = new Ext.TabPanel({
        title:"MY TAB",
        renderTo: 'my-tabs',
        width: 1230,
        height: 445,
        activeItem: 0,
        items: [billingGrid]
    });

});