function [ tnew, ynew ] = CN_STEP_FIXEDPOINT( told, yold, h, f )
%CN_STEP_FIXEDPOINT Summary of this function goes here
%   Detailed explanation goes here
    TOL = 10^-12;
    ynew = yold;
    tnew = told + h;
    done = 0;
    while done == 0
       newynew = yold + h / 2 * (f(told, yold) + f(tnew, ynew));  
       if (norm(newynew - ynew) < max(1, norm(newynew)) * TOL)
            ynew = newynew;
            return
       end
       ynew = newynew;
    end
end

