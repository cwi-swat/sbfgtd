set term png
set xlabel "Input characters"
set ylabel "Seconds"
set output "worst-case.png"
plot "worst-case.data" using 1:2 title "Recognizer" w linespoints, "worst-case.data" using 1:4 title "Parser" w linespoints
