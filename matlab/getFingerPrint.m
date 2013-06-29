function[minLambda] = getFingerPrint(pointGrid)

debug = 0;

N = size(pointGrid,1);
K = sum(pointGrid(pointGrid == 1));

grid_x = meshgrid(1:N, 1:N);
grid_y = meshgrid(1:N, 1:N)';

%pointGrid = createGrid(N, K);

x = grid_x(pointGrid > 0);
y = grid_y(pointGrid > 0);

minLambda = 0;
minGrid = 0;

try
    k = convhull(x,y);
catch 
    return;
end

s = [x(k), y(k)];

for i=1:size(s,1)-1
    l_Grid = labelPoints(pointGrid, N, K, s, i);
    [lambdaMatrix, colinearPointsVector] = getLambdaMatrix(l_Grid);
%     if sum(colinearPointsVector) > 0
%         minLambda = 0;
%         return;
%     end
    if minLambda == 0
        minGrid = l_Grid;
        minLambda = lambdaMatrix;
    else
        diff = triu(lambdaMatrix, 1) - triu(minLambda, 1);
        found = 0;
        for row=2:K-1
            for col=row+1:K
                if diff(row,col) > 0
                    found = 1;
                    break;
                end
                if diff(row,col) < 0
                    found = 2;
                    break;
                end
            end
            if found > 0
                break;
            end
        end
        if found == 2
            minLambda = lambdaMatrix;
            minGrid = l_Grid;
        end
    end
    
    if debug == 1
        l = l_Grid(l_Grid > 0);

        labels = cellstr(num2str(l));

        figure;
        hold all;
        plot(x(k), y(k), '-r', 'LineWidth', 2);
        plot(x, y, 'b+', 'LineWidth', 3);
        text(x, y, labels, 'VerticalAlignment','bottom', ...
                           'HorizontalAlignment','right');
        axis([1 N 1 N]);
        grid on;
        set(gca,'XTick',0:1:N);
        set(gca,'YTick',0:1:N);

        %f = dir('output/*.png');
        %num = size(f,1)+1;
        %print('-dpng', ['output/',num2str(num),'.png']);
        %clf;
    end
end


