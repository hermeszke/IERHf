// Agent human in project parkInt.mas2j

/* Initial goals */
!visit_park.

/* Plans */
+!visit_park
  <- generateDest; // Rethink destination
     mightLitter; // Bad visitor!
     walk; // Just walking around
     !visit_park. // Visiting the part is still their goal
