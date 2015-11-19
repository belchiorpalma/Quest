// JavaScript Document
//--> adjust on graphics to show point circle
function chartPoint(chart){
		// Listen for draw events on the bar chart
		chart.on('draw', function(data) {
		  // If this draw event is of type bar we can use the data to create additional content
		  if(data.type === 'bar') {
			// We use the group element of the current series to append a simple circle with the bar peek coordinates and a circle radius that is depending on the value
			// bar size
			//data.element.attr({
			//  style: 'stroke-width: 10px'
			//});
			
		   var label, labelText, barLength, labelWidth, barClasses,
					barWidth = 30,
					barHorizontalCenter = (data.x1 + (data.element.width() * 1.00)),
					barVerticalCenter =  (data.y1 + (barWidth * 0.08));
					
		// add the custom label text as an attribute to the bar for use by a tooltip
				data.element.attr({ label: labelText }, "ct:series");
			// create a custom label element to insert into the bar
				label = new Chartist.Svg("text");
				label.text(data.value.x);
				label.attr({
					x: barHorizontalCenter,
					y: barVerticalCenter,
					"text-anchor": "middle",
					style: "font-family: 'proxima-nova-alt', Helvetica, Arial, sans-serif; font-size: 12px; fill: white"
				});
			   
		
			// We use the group element of the current series to append a simple circle with the bar peek coordinates and a circle radius that is depending on the value
			data.group.append(new Chartist.Svg('circle', {
			  cx: data.x2,
			  cy: data.y2,
			  r: 12//Math.abs(Chartist.getMultiValue(data.value.x)) * 2 + 5
			}, 'ct-slice-pie'));
				
		  // add the new custom text label to the bar
				data.group.append(label); 
		  }
		});
}
		//<-- adjust on grahics to show point circle