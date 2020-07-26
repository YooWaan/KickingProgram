
var nodeData = {
    "name": "TOPICS", "children": [{
        "name": "Topic A", "children": [
          {"name": "Sub A1", "size": 4}, {"name": "Sub A2", "size": 4}
        ]
    }, {
        "name": "Topic B",  "children": [
          {"name": "Sub B1", "size": 3}, {"name": "Sub B2", "size": 3},
          {"name": "Sub B3", "size": 3}
        ]
    }, {
        "name": "Topic C", "children": [
          {"name": "Sub A1", "size": 4}, {"name": "Sub A2", "size": 4}
        ]
    }]
};


var width = 500;  // <-- 1
var height = 500;
var radius = Math.min(width, height) / 2;  // < -- 2
var color = d3.scaleOrdinal(d3.schemeCategory20b);   // <-- 3


var g = d3.select('svg')  // returns a handle to the <svg> element
    .attr('width', width)  // sets the width of <svg> and then returns the <svg> element again
    .attr('height', height)  // (same as width)
    .append('g')  // adds a <g> element to the <svg> element. It returns the <g> element
    .attr('transform', 'translate(' + width / 2 + ',' + height / 2 + ')');  // takes the <g> element and moves the [0,0] center over and down

var partition = d3.partition()  // <-- 1
    .size([2 * Math.PI, radius]);  // <-- 2

var root = d3.hierarchy(nodeData)  // <-- 1
    .sum(function (d) { return d.size});  // <-- 2

partition(root);  // <-- 1
var arc = d3.arc()  // <-- 2
    .startAngle(function (d) { return d.x0 })
    .endAngle(function (d) { return d.x1 })
    .innerRadius(function (d) { return d.y0 })
    .outerRadius(function (d) { return d.y1 });

var format = d3.format(",d");

g.selectAll('path')  // <-- 1
    .data(root.descendants())  // <-- 2
    .enter()  // <-- 3
    .append('path')  // <-- 4
    .attr("display", function (d) { return d.depth ? null : "none"; })  // <-- 5
    .attr("d", arc)  // <-- 6
    .style('stroke', '#fff')  // <-- 7
    .style("fill", function (d) { return color((d.children ? d : d.parent).data.name); })
    .append("title")
    //.text(d => `d.data.name\nd.value`);
    .text(d => {
      return d.parent === null ? '' : `${d.ancestors().map(d => d.data.name).reverse().join("/")}\n${format(d.value)}`;
    });



function labelVisible(d) {
  return d.y1 <= 3 && d.y0 >= 1 && (d.y1 - d.y0) * (d.x1 - d.x0) > 0.03;
};

function labelTransform(d) {
  var x = (d.x0 + d.x1) / 2 * 180 / Math.PI;
  var y = (d.y0 + d.y1) / 2 * radius;
  return `rotate(${x - 90}) translate(${y},0) rotate(${x < 180 ? 0 : 180})`;
};

//var dlbl = g.append("g")
//.selectAll("text")
//.data(root.descendants().slice(1));

//console.log(dlbl);
//console.log(dlbl.enter());

// https://wizardace.com/d3-sunburst-base/

/*
  .attr("pointer-events", "none")
  .attr("text-anchor", "middle")
  .style("user-select", "none")
*/

function lblTf(d) {
  var x = (d.x0 + d.x1) / 2 * 180 / Math.PI;
  var yy = arc.centroid(d);
  console.log(d);
  return `translate(${yy}) rotate(${x})`;
};

g.selectAll("text")
  .data(root.descendants())
  .enter().append("text")
  .attr("fill", d => +labelVisible(d))
  //.attr("fill", 'black')
  .attr("transform", d => lblTf(d))
  //.attr("transform", d => labelTransform(d))
  .attr("dy", "0.35em")
  .attr("font", "10px")
  .attr("text-anchor", "middle")
  .text(d => d.parent === null ? '' : d.data.name);