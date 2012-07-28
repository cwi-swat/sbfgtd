set term png
set xlabel "Input characters"
set ylabel "Seconds"
set output "worst-case_with-epsilon.png"
plot "worst-case_with-epsilon.data" using 1:2 title "Recognizer" w linespoints, "worst-case_with-epsilon.data" using 1:4 title "Parser" w linespoints
