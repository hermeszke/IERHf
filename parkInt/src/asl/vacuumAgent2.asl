// Agent sample_agent in project parkInt

/* Initial beliefs and rules */
pos(metal,0,0).
pos(plast,0,12).
pos(paper,0,24).
checking_cells.

/* Initial goals */
+my_pos(X,Y)
   :  checking_cells
   <- !check_for_resources.
   
+!check_for_resources
   :  found(16)
   <- !stop_checking;
      !take(R,metal);
      !continue_mine.
      
+!check_for_resources
   :  found(32)
   <- !stop_checking;
      !take(R,paper);
      !continue_mine.
      
+!check_for_resources
   :  found(64)
   <- !stop_checking;
      !take(R,plast);
      !continue_mine.
      
 +!check_for_resources
   :  not found(16) & not found(32) & not found(64)
   <- move_to(next_cell).
   
 +!stop_checking : true
   <- ?my_pos(X,Y);
      +pos(back,X,Y);
      -checking_cells.
      
 +!take(R,B) : true
   <- mine(R);
      !go(B);
      drop(R).

+!continue_mine : true
   <- !go(back);
      -pos(back,X,Y);
      +checking_cells;
      !check_for_resources.

+!go(Position)
   :  pos(Position,X,Y) & my_pos(X,Y)
   <- true.

+!go(Position) : true
   <- ?pos(Position,X,Y);
      move_towards(X,Y);
      !go(Position).

/* @psf[atomic]
+!search_for(NewResource) : resource_needed(OldResource)
   <- +resource_needed(NewResource);
      -resource_needed(OldResource).

@pbf[atomic]
+building_finished : true
   <- .drop_all_desires;
      !go(boss).*/

