rm(list = ls());

require(strtracer);

simulation <- Simulation(
   configFile = "../config.xml",
   conserveFile = "../output/conserve_storage.txt",
   activeFile = "../output/active_storage.txt",
   streamLength = 590
   );

analysisWindow <- seq(from = 50, to = 350, by = 60) * 60;

plotActive(
   simulation,
   device = "windows",
   window = analysisWindow,
   xfactor = 1/60,
   xlab = "Time (min)",
   ylab = bquote(paste(
      "Concentration ( ",
      mu, "g ", ~ L^-1,
      ")"
      )),
   mar = c(4.5, 4.5, 1, 1)
   );

analysis <- HyperbolicAnalysisMCR(
   simulation = simulation,
   analysisWindow = analysisWindow,
   conserveColumn = 3:8,
   activeColumn = 3:8,
   reachLengths = c(90, 190, 290, 390, 490, 590),
   useRegression = TRUE
   );

runAll(analysis);

windows(width = 6, height = 12);
plot(analysis, xlab.cex = 0.8);

save(analysis, file = "./output/analysis_regress.RData");
