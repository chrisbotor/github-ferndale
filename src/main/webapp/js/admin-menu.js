/**
 * 
 */
Ext.onReady(function(){
	
	Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
	
	var adminBorderPanel = new Ext.Panel({
	    renderTo: document.body,
	    width: 1200,
            height: 530,
	    title: 'Portal Admin Menu',
	    layout:'border',
	    defaults: {
	        collapsible: true,
	        split: true,
	        bodyStyle: 'padding:15px'
	    },
	    items: [{
	        title: 'Alerts List',
	        region:'west',
	        margins: '5 0 0 0',
	        cmargins: '5 5 0 0',
	        width: 300,
	        minSize: 100,
	        maxSize: 250
	    },{
	        title: 'Main Content',
	        collapsible: false,
	        region:'center',
	        margins: '5 0 0 0',
	        xtype: 'tabpanel',
	        activeTab: 0, // index or id
	        items:[{
	            title: 'Add House',
	            html: 'This is tab 1 content.'
	        },{
	            title: 'Tab 2',
	            html: 'This is tab 2 content.'
	        },{
	            title: 'Tab 3',
	            html: 'This is tab 3 content.'
	        }]
	    }]
	});
	
});

