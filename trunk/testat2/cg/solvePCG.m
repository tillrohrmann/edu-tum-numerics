function [ x, m ] = solvePCG( A, b, x0, tol, maxit, ptype )
%SOLVEPCG Summary of this function goes here
%   Detailed explanation goes here

    m = 0;   
    
    switch ptype 
        case 1
            % Jacobi
            % B = D^-1
            B = diag(1 ./ diag(A));
        case 2
            % SGS
            x = zeros(size(A,1));
            m = 0;
            return
        case 3
            % SSOR
            x = zeros(size(A,1));
            m = 0;
            return
        otherwise
            B = eye(size(A,1));
    end
    b_norm_tol = norm(b) * tol;
    
    x_crnt = x0;
    r_crnt = b - A * x_crnt;
    z_crnt = B * r_crnt;
    p_crnt = z_crnt;
    r_z_crnt = dot(r_crnt, z_crnt);

    for k = 1:maxit      
        m = m + 1;
        
        A_p_crnt = A * p_crnt;
        ld = r_z_crnt / dot(p_crnt, A_p_crnt); 
        x_next = x_crnt + ld * p_crnt;
        r_next = r_crnt - ld * A_p_crnt;

        if (norm(r_next) < b_norm_tol)
            x = x_next;
            return
        end

        z_next = B * r_next;
        r_z_next = dot(r_next, z_next);
        p_crnt = z_next + r_z_next / r_z_crnt * p_crnt;

        x_crnt = x_next;
        r_crnt = r_next;
        r_z_crnt = r_z_next;
    end
	exception = MException('solvePCG reached maxit iterations. The iteration probably does not converge');
	throw(exception);
end

