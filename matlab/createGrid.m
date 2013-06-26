function[pointGrid] = createGrid(gridsize, numPoints)

pointGrid = zeros(gridsize, gridsize);

for i = 1:numPoints
    while 1
        x = uint32(rand(1,1) * gridsize + 1);
        y = uint32(rand(1,1) * gridsize + 1);
    
        if (x > gridsize)
          x = gridsize;
        end; 

        if (y > gridsize)
          y = gridsize;
        end;

        if pointGrid(y,x) == 0
            pointGrid(y,x) = 1;
            break;
        end
    end
end
