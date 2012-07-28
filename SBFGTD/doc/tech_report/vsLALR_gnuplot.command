set term png
set xlabel "Input characters"
set ylabel "Relative execution time in percent"
set output "vsLALR.png"
set xrange [0:1200000]
set xtics (200000,400000,600000,800000,1000000)
set format x "%10.0f"
set yrange [0:700]
set ytics (50,100,150,200,250,300,350,400,450,500,550,600,650);
plot "vsLALR.data" using ($1-50000):(100):(20000) title "JavaCup" w boxes fs solid 1, "vsLALR.data" using ($1-30000):($3 / $2 * 100):(20000) title "SGTDBF" w boxes fs solid 1, "vsLALR.data" using ($1-10000):($5 / $2 * 100):(20000) title "JSGLR" w boxes fs solid 1, "vsLALR.data" using ($1+30000):($6 / $2 * 100):(20000) title "Bison" w boxes fs solid 1, "vsLALR.data" using ($1+50000):($4 / $2 * 100):(20000) title "SGLR (No GC)" w boxes fs solid 1
