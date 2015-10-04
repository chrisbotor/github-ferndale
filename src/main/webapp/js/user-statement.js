Ext.onReady(function(){
    
    Ext.QuickTips.init();

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
    //var hId = Ext.String.format(document.getElementById("houseIds").value);
    
    var BillingBean = Ext.data.Record.create([
    {
        name: 'id',
        type: 'int'
    },
    {
        name: 'description',
        type: 'string'
    },
   {
        name: 'postedDate',
        type: 'string'
    }
    ]);


    var proxy = new Ext.data.HttpProxy({
        api: {
            read : 'user-get-monthly-statement.action'
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
            msg: res.responseText,
            icon: Ext.MessageBox.ERROR,
            buttons: Ext.Msg.OK
        });
    });


    var billingGrid = new Ext.grid.GridPanel({
        store: store,
        columns: [
        {
            header: "POSTED DATE",
            width: 170,
            sortable: true,
            dataIndex: 'postedDate'
        },{
            header: "DOWNLOAD STATEMENT",
            renderer:renderTopic,
            dataIndex: 'description',
            width: 170,
            sortable: true
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
        title:'MY MONTHLY STATEMENT'
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

function renderTopic(value, p, record){
    return String.format(
        '<b>\n\
                <a style="color:red" href="download/user-statement-pdf.action?{0}" target="_blank">\n\
            <b><img src="/homeportal/images/pdf.gif" alt="HTML tutorial" width="20" height="20"></b></a>\n\
                </b>',
        value,record.description);
}