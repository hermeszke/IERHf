// Agent sample_agent in project parkInt

/* Initial beliefs and rules */
pos(metal,0,0).
pos(plast,0,12).
pos(paper,0,24).
checking_cells.

/* Initial goals */
+my_pos(X,Y)
   :  checking_cells
   <- !check_for_garbage.
   
+!check_for_garbage
   :  found(16)
   <- !stop_checking;
      !take(R,metal);
      !continue_picking.
      
+!check_for_garbage
   :  found(32)
   <- !stop_checking;
      !take(R,paper);
      !continue_picking.
      
+!check_for_garbage
   :  found(64)
   <- !stop_checking;
      !take(R,plast);
      !continue_picking.
      
 +!check_for_garbage
   :  not found(R)
   <- move_to(next_cell).
   
 +!stop_checking : true
   <- ?my_pos(X,Y);
      +pos(back,X,Y);
      -checking_cells.
      
 +!take(R,B) : true
   <- pickUp(R);
      !go(B);
      drop(R).

+!continue_picking : true
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

