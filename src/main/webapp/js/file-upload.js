Ext.onReady(function(){
		
	// alert('on file-upload.js!');
		
	Ext.BLANK_IMAGE_URL = '/homeportaluat/ext-3.4.0/resources/images/default/s.gif';
	
	 var params = Ext.urlDecode(location.search.substring(1));
	    
	 var param = params.file;
	 //alert('param: ' + param);
	 
	if (param == 'water')
	{
		var uploadUrl = 'upload/water-reading.action';
	}
	else if (param == 'delinquent')
	{
		var uploadUrl = 'upload/delinquent.action';
	}
	
	// alert('uploadUrl: ' + uploadUrl);
	
	
		

	   var fileUploadForm = Ext.create('Ext.form.Panel', {
	        title: 'File Uploader',
	       //width: 400,
	       align:'center',
	        bodyPadding: 10,
	        frame: true,
	        renderTo: 'fi-form',    
	        items: [{
	            xtype: 'filefield',
	            name: 'file',
	            fieldLabel: 'File',
	            labelWidth: 50,
	            msgTarget: 'side',
	            allowBlank: false,
	            anchor: '100%',
	            buttonText: 'Select a File...'
	        }],

	        buttons: [{
	            text: 'Upload',
	            handler: function() {
	                var form = this.up('form').getForm();
	                if(form.isValid()){
	                    form.submit({
	                        url: uploadUrl,
	                        waitMsg: 'Uploading your file...',
	                        success: function(fp, o) {
	                            Ext.Msg.alert('Success', 'Your file has been uploaded.');
	                        }
	                    });
	                }
	            }
	        }]
	    });
	   
	   
	   var uploadMainPanel = new Ext.Panel({
	        id: 'upload-main-panel',
	        title: 'Upload File',
	        frame: true,
	        //cls: 'my-form-class',
	        bodyStyle:'padding:5px',
	       // labelWidth : 200,
	        //labelAlign : 'right',
	        renderTo: 'main-panel',
	        width : 420,
	        height : 160,
	        align:'center',
	       items: [{
	            align:'center',
	           // layout: 'column',
	            items: [fileUploadForm]
	        }],
	        
	    });


});
	
