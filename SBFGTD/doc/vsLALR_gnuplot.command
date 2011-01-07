set term png
set xlabel "Input characters"
set ylabel "Milliseconds"
set output "vsLALR.png"
plot "vsLALR.data" using 1:2 title "JFlex + JavaCup" w linespoints, "vsLALR.data" using 1:3 title "SGTDBF" w linespoints

